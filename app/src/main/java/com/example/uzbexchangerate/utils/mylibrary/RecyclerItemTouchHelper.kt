package com.example.uzbexchangerate.utils.mylibrary

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemTouchHelper(val context: Context, val widthSize: Float) {

    val itemTouchHelper = object : ItemTouchHelper.Callback() {
        private val limitScrollX = (widthSize * context.resources.displayMetrics.density).toInt()
        private var currentScrollX = 0
        private var currentScrollWhenInActive = 0
        private var initWhenInActive = 0f
        private var firstInActive = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = 0
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }


        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
            return Integer.MAX_VALUE.toFloat()
        }

        override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
            return Integer.MAX_VALUE.toFloat()
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX == 0f) {
                    currentScrollX = viewHolder.itemView.scrollX
                }

                if (isCurrentlyActive) {
                    var scrollOffset = currentScrollX + (-dX).toInt()
                    if (scrollOffset > limitScrollX) {
                        scrollOffset = limitScrollX.toInt()
                    } else if (scrollOffset < 0) {
                        scrollOffset = 0
                    }

                    viewHolder.itemView.scrollTo(scrollOffset, 0)
                } else {
                    //slide with auto anim
                    if (firstInActive) {
                        firstInActive = false
                        currentScrollWhenInActive = viewHolder.itemView.scrollX
                        initWhenInActive = dX
                    }

                    if (viewHolder.itemView.scrollX < limitScrollX) {
                        viewHolder.itemView.scrollTo((currentScrollWhenInActive * dX / initWhenInActive).toInt(), 0)
                    }
                }
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
            if (viewHolder.itemView.scrollX > limitScrollX) {
                viewHolder.itemView.scrollTo(limitScrollX.toInt(), 0)
            } else if (viewHolder.itemView.scrollX < 0) {
                viewHolder.itemView.scrollTo(0, 0)
            }
        }
    }

}