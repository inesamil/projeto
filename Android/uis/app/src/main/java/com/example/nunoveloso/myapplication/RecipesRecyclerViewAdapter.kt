package com.example.nunoveloso.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView

class RecipesRecyclerViewAdapter(context: Context)
    : RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mOnItemClickListener: OnItemClickListener? = null
    // Just for demonstration
    private val mRecipes = arrayOf("Strawberry cheesecake Fr.", "Lobster Ravioli and shrimp")
    private val mMinutes = arrayOf("30 minutes", "30 minutes")
    private val mDifficulty = arrayOf("Average", "Easy")

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.content_recipes_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each cell
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.nameItemText?.text = mRecipes[position]
        holder?.timeItemText?.text = mMinutes[position]
        holder?.difficultyItemText?.text = mDifficulty[position]
        holder?.ratingBarItem?.rating = when (mDifficulty[position]) {
            "Easy" -> 1f
            "Average" -> 2f
            "Hard" -> 3f
            else -> throw IllegalArgumentException("Rating bar receive illegal rating as argument")
        }
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mRecipes.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameItemText: TextView = itemView.findViewById(R.id.recipeNameText)
        internal var timeItemText: TextView = itemView.findViewById(R.id.timeText)
        internal var difficultyItemText: TextView = itemView.findViewById(R.id.difficultyText)
        internal var ratingBarItem: RatingBar = itemView.findViewById(R.id.ratingBar)
        internal var tryItemButton: Button = itemView.findViewById(R.id.tryBtn)

        init {
            tryItemButton.setOnClickListener {
                mOnItemClickListener?.onItemClick(it, adapterPosition)
            }
        }
    }

    // parent activity will implement this method to respond to click events
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}