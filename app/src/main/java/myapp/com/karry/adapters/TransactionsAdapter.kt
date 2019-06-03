package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.transactions_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Transaction
import myapp.com.karry.activities.TransactionDetailsActivity

class TransactionViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class TransactionsAdapter(private val transactionList: List<Transaction>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TransactionViewHolder>() {

    override fun getItemCount(): Int {
        return transactionList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.transactions_row, parent, false)
        return TransactionViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.view.transactionPrice.text = transaction.price
        holder.view.transactionName.text = transaction.name
        holder.view.transactionDescription.text = transaction.description
        holder.view.transactionCard.setOnClickListener { v -> loadTransaction(v.context, transaction) }
    }

    private fun loadTransaction(c: Context, transaction: Transaction) {
        val intent = Intent(c, TransactionDetailsActivity::class.java)
        intent.putExtra("EVENT_ID", transaction.id)
        c.startActivity(intent)
    }
}