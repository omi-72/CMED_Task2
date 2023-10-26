package com.example.cmed_task2.networkCommunication

import android.util.Log
import com.google.gson.JsonArray
import retrofit2.Response

class CharacterModelList(
    var name: String = "",
    var alternate_names : MutableList<String> = mutableListOf(),
    var species: String = "",
    var gender: String = "",
    var houseName: String = "",
    var dateOfBirth :Double = 0.0,
    var yearOfBirth : Double = 0.0,
    var isWizard: Boolean = false,
    var ancestry: String = "",
    var eyeColour: String = "",
    var hairColour: String = "",
    var wand : MutableList<String> = mutableListOf(),
    var patronus :String = "",
    var isHogwartsStudent: Boolean = false,
    var isHogwartsStaff: Boolean = false,
    var actorName: String = "",
    var isAlive: Boolean = false,
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

//
//                            if(productJsonObject.get("id")!= null){
//                                //item.
//                            }

                            if(productJsonObject.get("name")!= null){
                                item.name = productJsonObject.get("name").asString
                            }
                            if (productJsonObject.get("alternate_names")!=null){
                                val alternate_namesJsonArray = productJsonObject.get("alternate_names").asJsonArray
                                for (i in 0 until alternate_namesJsonArray.size()){

                                    if (alternate_namesJsonArray.get(i) != null) {
                                        val ingredient = alternate_namesJsonArray.get(i).asString
                                        item.alternate_names.add(ingredient)
                                    }
                                }
                            }
                            if(productJsonObject.get("species")!= null){
                                item.species = productJsonObject.get("species").asString
                            }

                            if(productJsonObject.get("gender")!= null){
                                item.gender = productJsonObject.get("gender").asString
                            }
                            if(productJsonObject.get("house")!= null) {
                                item.houseName = productJsonObject.get("house").asString
                            }


                            if(productJsonObject.has("dateOfBirth")){
                                item.dateOfBirth = productJsonObject.get("dateOfBirth").asDouble
                            }

                            if(productJsonObject.has("yearOfBirth")){
                                item.yearOfBirth = productJsonObject.get("yearOfBirth").asDouble
                            }

                            if (productJsonObject.has("isWizard") && !productJsonObject.isNull("isWizard")) {
                                val wizard = productJsonObject.getBoolean("isWizard")
                                generalAlerts.first{it.type == gTAAlertTypeEnum }.isWizard  = productJsonObject.getBoolean("isWizard")
                            }

                            if(productJsonObject.get("ancestry")!= null){
                                item.ancestry = productJsonObject.get("ancestry").asString
                            }
                            if(productJsonObject.get("eyeColour")!= null){
                                item.eyeColour = productJsonObject.get("eyeColour").asString
                            }

                            if(productJsonObject.get("hairColour")!= null){
                                item.hairColour = productJsonObject.get("hairColour").asString
                            }

                            if (productJsonObject.get("wand")!=null){
                                val wandJsonObject = productJsonObject.get("wand").asJsonObject
                                for (i in 0 until wandJsonObject.size()){

                                    if (wandJsonObject.get("") != null) {
                                        val ingredient = wandJsonObject.get("").asString
                                        item.wand.add(ingredient)
                                    }
                                }
                            }
                            if(productJsonObject.get("patronus")!= null){
                                item.patronus = productJsonObject.get("patronus").asString
                            }

                            if (productJsonObject.has("hogwartsStudent") && !productJsonObject.isNull("isWizard")) {
                                val wizard = productJsonObject.getBoolean("isWizard")
                                generalAlerts.first{it.type == gTAAlertTypeEnum }.isWizard  = productJsonObject.getBoolean("isWizard")
                            }

                            if (productJsonObject.has("hogwartsStaff") && !productJsonObject.isNull("isWizard")) {
                                val wizard = productJsonObject.getBoolean("isWizard")
                                generalAlerts.first{it.type == gTAAlertTypeEnum }.isWizard  = productJsonObject.getBoolean("isWizard")
                            }

                            if(productJsonObject.get("actor")!= null){
                                item.actorName = productJsonObject.get("actor").asString
                            }

                            if (productJsonObject.has("hogwartsStaff") && !productJsonObject.isNull("isWizard")) {
                                val wizard = productJsonObject.getBoolean("isWizard")
                                generalAlerts.first{it.type == gTAAlertTypeEnum }.isWizard  = productJsonObject.getBoolean("isWizard")
                            }



                            if (productJsonObject.get("image")!= null){
                                item.imageThumbnail = productJsonObject.get("image").asString
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