package com.cuncis.sampletourguide.utils

import android.view.View
import androidx.core.view.postDelayed
import androidx.core.widget.NestedScrollView
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnCompleteListener
import me.toptas.fancyshowcase.listener.OnQueueListener
import java.util.*
import kotlin.collections.ArrayList

class TooltipQueue : OnQueueListener, TooltipHandler.TooltipSkipListener {

  private val queue: Queue<FancyShowCaseView> = LinkedList()
  private val anchors: Queue<View?> = LinkedList()
  private val tooltipHandlers: ArrayList<TooltipHandler?> = ArrayList()
  private var current: FancyShowCaseView? = null
  private var completeListener: OnCompleteListener? = null
  private var scrollParent: NestedScrollView? = null

  companion object {
    /**
     * Initialize the class with this function.
     *
     * @param scrollParent the [NestedScrollView] parent containing the anchor View(s)
     */
    fun inside(scrollParent: NestedScrollView?) = TooltipQueue().apply {
      this.scrollParent = scrollParent
    }
  }

  /**
   * Function to add [TooltipHandler]s to the queue
   *
   * @param tooltipHandlers the [TooltipHandler] instances that you want to show, in ordered manner.
   *
   * The [tooltipHandlers] should be created using the following extension functions:
   *  - Fragment.[roundTooltipOf] to show round focus
   *  - Fragment.[rectTooltipOf] to show round-rectangular focus
   */
  fun withHandlers(vararg tooltipHandlers: TooltipHandler?) = apply {
    this.tooltipHandlers.addAll(tooltipHandlers.toList().apply {
      forEach { it?.onSkipListener = this@TooltipQueue }
    })
  }

  /**
   * Function to start the queue and show the first tooltip
   */
  fun startShowing() {
    tooltipHandlers.forEach { add(it?.tooltipView, it?.anchor) }
    show()
  }

  /**
   * Adds a FancyShowCaseView and its anchor View to the queue
   *
   * @param showCaseView the view that should be added to the queue
   * @param anchor the view that should be the anchor for the [showCaseView]
   */
  fun add(showCaseView: FancyShowCaseView?, anchor: View?) {
    if (showCaseView != null && anchor != null) {
      queue.add(showCaseView)
      anchors.add(anchor)
    }
  }

  /**
   * Starts displaying all views in order of their insertion in the queue, one after another
   */
  fun show() {
    if (queue.isNotEmpty()) {
      current = queue.poll()?.apply {
        anchors.poll()?.let { currentAnchor ->
          if (!isShownBefore()) scrollToView(currentAnchor)
        }
        queueListener = this@TooltipQueue
        scrollParent?.postDelayed(500) { show() }
      }
    } else {
      completeListener?.onComplete()
    }
  }

  /**
   * Function to scroll the specified [scrollParent] to scroll to the [anchor] with some space on top.
   * the [anchor] must be a direct descendant of the [scrollParent] for the scrolling to work properly
   */
  private fun scrollToView(anchor: View) {
    val scrollHeight = 600 / 6
    val scrollY = if (anchor.top <= scrollHeight) scrollHeight else anchor.top - scrollHeight
    scrollParent?.smoothScrollTo(0, scrollY)
  }

  /**
   * Cancels the queue
   * @param hideCurrent hides current FancyShowCaseView
   */
  fun cancel(hideCurrent: Boolean = true) {
    if (hideCurrent) current?.hide()

    if (queue.isNotEmpty()) queue.clear()
  }

  override fun skipAll() = cancel()

  override fun onNext() {
    show()
  }
}