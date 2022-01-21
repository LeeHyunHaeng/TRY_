package com.yjk.sample._1_Test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yjk.sample.R
import com.yjk.sample._1_Test.datamodule.Data_Kt

class Adapter_kt(private val context : Context,private val mList : ArrayList<Data_Kt>) : RecyclerView.Adapter<Adapter_kt.MyViewHolder>() {
    val TAG = "FOOD"
    private lateinit var callback: OnDeleteCallback



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_kt.MyViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.recycelerview_items,parent,false)
        return Adapter_kt.MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: Adapter_kt.MyViewHolder, position: Int) {
        var data : Data_Kt = mList[position]

        holder.tv_title.text = data.title
        holder.tv_context.text = data.context

    //롱클릭시 아이템 삭제
        holder.root.setOnLongClickListener{
            callback.DeleteItem(it,position)
            Toast.makeText(context, "삭제되었습니다!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onBindViewHolder: it = ${it}, position = ${position}")

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
//    위에 getItemCount()는 지금 블록이 본문인 메서드이니깐 식이 본문인 메서드로 변경하면

//    override fun getItemCount(): Int = mList.size

//    이렇게 같은 내용의 메서드를 두가지 방법으로 표현할 수 있다.


    class MyViewHolder(private var view : View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_context = view.findViewById<TextView>(R.id.tv_contents)
        val root = view.findViewById<LinearLayout>(R.id.root_kt)


    }

//    fun OnDeleteCallbackListener(callback : (View,Int) -> Unit){
//        this.callback = object : OnDeleteCallback {
//
//            override fun DeleteItem(v: View, position: Int) {
//                callback(v,position)
//            }
//        }
//    }

    fun setOnDeleteListener(callback: OnDeleteCallback) {
        this.callback = callback
    }

    interface OnDeleteCallback {
        fun DeleteItem(v : View, position : Int)
    }


}