package com.mrr.animalshelter.core.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.random.Random

abstract class BaseActivity : AppCompatActivity() {

    private val mOnActivityResultMap = mutableMapOf<Int, (resultCode: Int, data: Intent?) -> Unit>()
    private var mPermissionsRequiredOperation: PermissionsRequiredOperation? = null

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mOnActivityResultMap[requestCode]?.invoke(resultCode, data)
        mOnActivityResultMap.remove(requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val hasPermissions = checkPermissions(permissions)
        if (hasPermissions) {
            mPermissionsRequiredOperation?.onExecute(requestCode, permissions)
        } else {
            if (shouldShowRequestPermissionRationale(permissions)) {
                mPermissionsRequiredOperation?.onPermissionsDenied()
            } else {
                mPermissionsRequiredOperation?.onNotShowPermissionRationaleUi()
            }
        }
        mPermissionsRequiredOperation = null
    }

    protected fun startActivityForResult(
        intent: Intent,
        onActivityResult: (resultCode: Int, data: Intent?) -> Unit
    ) {
        val requestCode = getRandomRequestCode()
        mOnActivityResultMap[requestCode] = onActivityResult
        startActivityForResult(intent, requestCode)
    }

    protected fun withPermissions(
        requirePermissions: Array<String>,
        onPreExecute: ((onUserConfirm: () -> Unit) -> Unit) = { onUserConfirm -> onUserConfirm() },
        onExecute: () -> Unit,
        onPermissionsDenied: (() -> Unit) = { Log.d(TAG, "on permissions denied") },
        onNotShowPermissionRationaleUi: (() -> Unit) = { Log.d(TAG, "on not show permission rationale ui") }
    ) {
        onPreExecute {
            val requestCode = Random.nextInt(0, Int.MAX_VALUE)
            mPermissionsRequiredOperation = createOperation(
                requirePermissions,
                onExecute,
                onPermissionsDenied,
                onNotShowPermissionRationaleUi
            )
            mPermissionsRequiredOperation?.onExecute(requestCode, mPermissionsRequiredOperation?.onRequirePermissions())
        }
    }

    private fun getRandomRequestCode(): Int {
        val randomInt = Random.nextInt(0, Short.MAX_VALUE.toInt())
        if (mOnActivityResultMap.containsKey(randomInt)) {
            return getRandomRequestCode()
        }
        return randomInt
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }
        return true
    }

    private fun shouldShowRequestPermissionRationale(permissions: Array<String>?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions?.forEach { permission ->
                if (!shouldShowRequestPermissionRationale(permission)) {
                    return false
                }
            }
        }
        return true
    }

    private fun createOperation(
        requirePermissions: Array<String>,
        onExecute: () -> Unit,
        onPermissionsDenied: () -> Unit,
        onNotShowPermissionRationaleUi: () -> Unit
    ): PermissionsRequiredOperation {

        return object : PermissionsRequiredOperation {
            override fun onRequirePermissions(): Array<String> {
                return requirePermissions
            }

            override fun onExecute(requestCode: Int, requiredPermissions: Array<String>?) {
                requiredPermissions?.let {
                    val hasPermissions: Boolean = checkPermissions(requiredPermissions)
                    if (!hasPermissions) {
                        requestPermissions(requiredPermissions, requestCode)
                        return
                    }
                    onExecute()
                } ?: onExecute()
            }

            override fun onPermissionsDenied() {
                onPermissionsDenied()
            }

            override fun onNotShowPermissionRationaleUi() {
                onNotShowPermissionRationaleUi()
            }
        }
    }

    private interface PermissionsRequiredOperation {
        fun onRequirePermissions(): Array<String>
        fun onExecute(requestCode: Int, requiredPermissions: Array<String>?)
        fun onPermissionsDenied()
        fun onNotShowPermissionRationaleUi()
    }
}