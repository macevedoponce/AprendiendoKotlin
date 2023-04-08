package com.acevedo.superheroapp_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuperHeroAdapter(var superheroList:List<SuperHeroItemResponse> = emptyList(),
                       private val onItemSelected:(String) -> Unit //función lambda
) : RecyclerView.Adapter<SuperHeroViewHolder>() {

    fun updateList(superheroList:List<SuperHeroItemResponse>){
        this.superheroList = superheroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        //aca se pone el item a mostrar

        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_superhero,parent,false)
        )
    }

    override fun getItemCount()= superheroList.size


    override fun onBindViewHolder(viewholder: SuperHeroViewHolder, position: Int) {
        viewholder.bind(superheroList[position], onItemSelected)
    }
}