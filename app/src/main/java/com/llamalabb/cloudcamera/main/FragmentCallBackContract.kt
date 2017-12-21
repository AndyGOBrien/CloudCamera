package com.llamalabb.cloudcamera.main

/**
 * Created by andy on 12/1/17.
 */
interface FragmentCallBackContract {
    interface FragmentHolder: HomeCallBack, ProfileCallBack, GalleryCallBack{

    }

    interface HomeCallBack{
        fun logout()
    }

    interface ProfileCallBack{

    }

    interface GalleryCallBack{

    }


}