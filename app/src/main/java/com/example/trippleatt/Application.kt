package com.example.trippleatt

import android.app.Application
import com.example.trippleatt.data.Repository
import com.example.trippleatt.ui.bSU3.BusinessSU3VMF
import com.example.trippleatt.ui.bSU5.BusinessSU5VMF
import com.example.trippleatt.ui.businessDetails.BusinessDetailsVMF
import com.example.trippleatt.ui.businessLS.BusinessLSVMF
import com.example.trippleatt.ui.businessSU.BusinessSUVMF
import com.example.trippleatt.ui.loginScreen.LoginScreenVM
import com.example.trippleatt.ui.loginScreen.LoginScreenVMF
import com.example.trippleatt.ui.otpV.OtpVVMF
import com.example.trippleatt.ui.signUpScreen.SignUpScrVMF
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Application : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from singleton { AppPreferences(instance()) }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { LoginScreenVM(instance()) }

        bind() from provider { LoginScreenVMF(instance()) }
        bind() from provider { BusinessLSVMF(instance()) }
        bind() from provider { BusinessSUVMF(instance()) }
        bind() from provider { BusinessSU3VMF(instance()) }
        bind() from provider { BusinessSU5VMF(instance()) }
        bind() from provider { BusinessDetailsVMF(instance()) }
        bind() from provider { OtpVVMF(instance()) }
        bind() from provider { SignUpScrVMF(instance()) }
    }
}