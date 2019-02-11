package com.rntappay;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import tech.cherri.tpdirect.api.TPDCard.CardType;
import tech.cherri.tpdirect.api.TPDCardInfo;
import tech.cherri.tpdirect.api.TPDMerchant;
import tech.cherri.tpdirect.api.TPDSamsungPay;
import tech.cherri.tpdirect.api.TPDServerType;
import tech.cherri.tpdirect.api.TPDSetup;
import tech.cherri.tpdirect.callback.TPDTokenFailureCallback;
import tech.cherri.tpdirect.callback.TPDTokenSuccessCallback;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class TapPayModule extends ReactContextBaseJavaModule {
    private static final String TAG = "TapPayModule";
    private TPDSamsungPay tpdSamsungPay;

    public TapPayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        TPDSetup.initInstance(reactContext, 11341,
                "app_whdEWBH8e8Lzy4N6BysVRRMILYORF6UxXbiOFsICkz0J9j1C0JUlCHv1tVJC", TPDServerType.Sandbox);
        TPDMerchant tpdMerchant = new TPDMerchant();
        tpdMerchant.setMerchantName("React Native TapPay");
        tpdMerchant.setSupportedNetworks(new CardType[]{
                CardType.Visa, CardType.AmericanExpress, CardType.MasterCard, CardType.JCB});
        tpdMerchant.setSamsungMerchantId("tech.cherri.samsungpayexample");
        tpdMerchant.setCurrencyCode("TWD");

        tpdSamsungPay = new TPDSamsungPay(getReactApplicationContext(),
                "5bf84c6040dc4b8e907aa1", tpdMerchant);

        // TODO: check isSamsungPayAvailable
    }

    @Override
    public String getName() {
        return "TapPayModule";
    }

    @ReactMethod
    public void samsungPay(final Promise promise) {
        tpdSamsungPay.getPrime("10", "0", "0", "10",
            new TPDTokenSuccessCallback() {
                @Override
                public void onSuccess(final String s, TPDCardInfo tpdCardInfo) {
                    Log.i(TAG, "tpdSamsungPay.getPrime.onSuccess");
                    promise.resolve(s);
                }
            },
            new TPDTokenFailureCallback() {
                @Override
                public void onFailure(int i, String s) {
                    Log.i(TAG, "tpdSamsungPay.getPrime.onFailure");
                    promise.reject(String.valueOf(i), s);
                }
            });
    }

}
