package ru.myfitness.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.myfitness.R
import ru.myfitness.databinding.PlanListItemBinding

class PlanAdapter(var listener: PlanAdapter.Listener) :
    ListAdapter<PlanModel, PlanAdapter.PlanHolder>(PlanAdapter.MyComparator()){
    class PlanHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PlanListItemBinding.bind(view)
        fun setData(plan: PlanModel, listener: Listener) = with(binding) {
            val nameValue = "План " + "${adapterPosition + 1} " + "${plan.name}"
            name.text = nameValue
            val exCounter =
                "Количество дней: " + plan.array.size.toString()
            counter.text = exCounter
            checkBox.isChecked = plan.isDone
            itemView.setOnClickListener {
                listener.onClick(plan.copy(planNumber = adapterPosition + 1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.days_list_item, parent, false)
        return PlanHolder(view)
    }

    override fun onBindViewHolder(holder: PlanHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class MyComparator : DiffUtil.ItemCallback<PlanModel>() {
        override fun areItemsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onClick(plan: PlanModel)
    }
}