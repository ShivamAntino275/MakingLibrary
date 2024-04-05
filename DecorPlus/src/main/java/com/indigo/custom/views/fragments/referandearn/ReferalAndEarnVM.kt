package com.indigo.custom.views.fragments.referandearn

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.indigo.custom.R
import com.indigo.custom.customview.ReferAndEarnView
import com.indigo.custom.models.CreateRefferalsRequest
import com.indigo.custom.models.GetAllreferralResponse
import com.indigo.custom.networks.NetworkProcess
import com.indigo.custom.networks.Repository
import com.indigo.custom.networks.RetrofitClient
import com.indigo.custom.recycleradapter.RecyclerAdapter
import com.indigo.custom.utils.hideProgress
import com.indigo.custom.utils.hideSoftKeyboard
import com.indigo.custom.utils.showAlartMessage
import com.indigo.custom.utils.showProgress
import com.indigo.custom.utils.showToast
import com.indigo.custom.views.activity.CustomScreenActivity
import com.indigo.custom.views.activity.MainDecorActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class ReferalAndEarnVM() : ViewModel() {
    private val repository = Repository()
    var createRefferal = ObservableField(CreateRefferalsRequest())
    var context: WeakReference<FragmentActivity>?=null

    var currentPage = 1
    var isScroll = true

    var clearFocus = MutableSharedFlow<Boolean>()

    val referalAdapter by lazy {
        RecyclerAdapter<GetAllreferralResponse.Data.ReferalData>(
            R.layout.item_referral,
            true
        )
    }



     fun getReferralsApi(isPagination:Boolean=false) {
         context?.get()?.showProgress()
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().getAllReferrals(page = currentPage)
            }.catch {
                hideProgress()
            }.collectLatest {
                when (it) {
                    is NetworkProcess.Loading -> {

                    }

                    is NetworkProcess.Success -> {
                        hideProgress()
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                if (isPagination){
                                    isScroll = if (it.data.data.data.isNotEmpty()){
                                        referalAdapter.addNewItems(it.data.data.data ?: emptyList())
                                        true
                                    }else{
                                        false
                                    }
                                }else{
                                    isScroll=true // it is use for if current page is last then no need to hit api
                                    Log.d("getReferralsApi", "getReferralsApi: ${it.data.data.data.size}")
                                    referalAdapter.addItems(it.data.data.data ?: emptyList())
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    }

                    is NetworkProcess.Error -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            hideProgress()
                            context?.get()?.showToast(it.message)
                        }
                    }
                }
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.submit_referral -> {
                if (createRefferal.get()?.name.isNullOrEmpty()){
                    view.context.showToast("Please enter name")
                }else if (createRefferal.get()?.phone_number.isNullOrEmpty()) {
                    view.context.showToast("Please enter Phone Number")
                }else{
                    view.hideSoftKeyboard()
                    createrefferal(view)
                }
            }
        }
    }

    private fun createrefferal(view: View) {
        view.context.showProgress()
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().createReferrals(request = createRefferal.get()?:CreateRefferalsRequest())
            }.collectLatest {
                when (it) {
                    is NetworkProcess.Loading -> {}
                    is NetworkProcess.Success -> {
                        hideProgress()
                        withContext(Dispatchers.Main) {
                            if (it.data.data !=null && it.data.data.status == 200){
                                Log.d("Create referrals", "createrefferal: ${it}")
                                createRefferal.set(CreateRefferalsRequest())
                                clearFocus.emit(true)
                                currentPage=1
                                getReferralsApi()
                                showAlartMessage("Referral has been submitted Successfully ",(context?.get() as MainDecorActivity))

                            }
                        }
                    }
                    is NetworkProcess.Error -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            hideProgress()
                            context?.get()?.showToast(it.message)

                        }
                    }
                }
            }
        }
    }
}