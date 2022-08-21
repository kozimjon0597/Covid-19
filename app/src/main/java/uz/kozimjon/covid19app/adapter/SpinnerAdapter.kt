package uz.kozimjon.covid19app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.model.Spinner

class SpinnerAdapter(context: Context, list: List<Spinner>) :
    ArrayAdapter<Spinner?>(context, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_spinner, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val ivImage = view.findViewById<ImageView>(R.id.ivImage)

        tvName.text = item!!.name
        ivImage.setImageResource(item.image)

        return view
    }
}