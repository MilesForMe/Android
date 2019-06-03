package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_transaction_details.*
import myapp.com.karry.R
import myapp.com.karry.adapters.SharedImagesAdapter
import myapp.com.karry.entity.SharedImage
import myapp.com.karry.entity.Transaction
import myapp.com.karry.entity.User

class TransactionDetailsActivity : AppCompatActivity() {
    private val sharedImageList: ArrayList<SharedImage> = arrayListOf()
    private val author: User = User("toto", "Nicolas", "Leroy", "0646862158", "nico@kkarry.fr", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg", "", "Pas encore de description")
    private val transaction = Transaction("test", "Hahahahhahahahha", "Huilde d'olive", "24, 30 €", sharedImageList, author)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        fillSharedImages()
        setTransactionDetails()
        setSharedImagesView()
        closeTransactionDetailsButton.setOnClickListener { onBackPressed() }
    }

    private fun setTransactionDetails() {
        transactionName.text = transaction.name
        val fullname = "${transaction.author.firstname} ${transaction.author.lastname}"
        transactionAuthorName.text = fullname

        Glide
            .with(this)
            .load(transaction.author.profilePicture)
            .circleCrop()
            .into(transactionAuthorImage)
    }

    private fun setSharedImagesView() = runOnUiThread {
        transactionSharedImagesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        transactionSharedImagesList.adapter = SharedImagesAdapter(sharedImageList)
    }

    private fun fillSharedImages() {
        sharedImageList.add(
            SharedImage("https://images-na.ssl-images-amazon.com/images/I/81Kiorfqx4L._SX679_.jpg", "toto", "Test"))
        sharedImageList.add(
            SharedImage("https://images-na.ssl-images-amazon.com/images/I/81Kiorfqx4L._SX679_.jpg", "toto", "Test"))
        sharedImageList.add(
            SharedImage("https://images-na.ssl-images-amazon.com/images/I/81Kiorfqx4L._SX679_.jpg", "toto", "Test"))
    }

}
