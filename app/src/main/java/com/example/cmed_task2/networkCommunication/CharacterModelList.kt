package com.example.cmed_task2.networkCommunication

import android.util.Log
import com.google.gson.JsonArray
import retrofit2.Response

class CharacterModelList(
    var name: String = "",
    var actorName: String = "",
    var houseName :String = "",
    var imageThumbnail : String? = null

) {
    fun loadApi(callback: (Boolean, List<CharacterModelList>?, String?) -> Unit) {
        ServerManager().getRequest(
            url = ApiConstants.API_PRODUCTS,
            listener = object : RequestListener {
                override fun onSuccess(response: Any?) {

                    Log.d("Response",response.toString())
                    val responseObject = (response as? Response<*>)
                    val productsJsonArray = responseObject?.body() as JsonArray

                    val itemList : MutableList<CharacterModelList> = mutableListOf()


                    for (index in 0 until productsJsonArray.size()){
                        if (productsJsonArray.get(index) != null) {
                            val productJsonObject = productsJsonArray.get(index).asJsonObject
                            val item = CharacterModelList()

//                            if(productJsonObject.get("id")!= null){
//                                //item.
//                            }

                            if(productJsonObject.get("name")!= null){
                                item.name = productJsonObject.get("name").asString
                            }

                            if(productJsonObject.get("actor")!= null){
                                item.actorName = productJsonObject.get("actor").asString
                            }

                            if (productJsonObject.get("image")!= null){
                                item.imageThumbnail = productJsonObject.get("image").asString
                            }

                            if(productJsonObject.get("house")!= null) {
                                item.houseName = productJsonObject.get("house").asString
                            }

//                            if (productJsonObject.get("ingredients")!=null){
//                                val ingredientsJsonArray = productJsonObject.get("ingredients").asJsonArray
//                                for (i in 0 until ingredientsJsonArray.size()){
//
//                                    if (ingredientsJsonArray.get(i) != null) {
//                                        val ingredient = ingredientsJsonArray.get(i).asString
//                                        item.ingredients.add(ingredient)
//                                    }
//                                }
//
//                            }
//
//                            Log.d("id->",productJsonObject.get("id").asString)
//                            Log.d("title->",productJsonObject.get("title").asString)
//                            Log.d("description->",productJsonObject.get("description").asString)
////                            Log.d("thumbnail->",productJsonObject.get("thumbnail").asString)

                            //val item = CustomModel(productJsonObject.get("title").asString,productsJsonArray.get(index).asJsonObject.get("description").asString )
                            itemList.add(item)
                        }
                    }

                    callback(true, itemList, null)
                }

                override fun onError(error: String) {
                    Log.e("Error",error)
                    callback(false, null, error)
                }

            }
        )
    }
}