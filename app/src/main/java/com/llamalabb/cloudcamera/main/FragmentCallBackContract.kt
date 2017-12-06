package com.llamalabb.cloudcamera.main

import com.llamalabb.cloudcamera.main.photo.AddPhotoFragment

/**
 * Created by andy on 12/1/17.
 */
interface FragmentCallBackContract {
    interface FragmentHolder: AddPhotoCallBack, HomeCallBack, ProfileCallBack, GalleryCallBack{

    }

    interface AddPhotoCallBack{
        fun launchGallery()
    }

    interface HomeCallBack{
        fun logout()
    }

    interface ProfileCallBack{

    }

    interface GalleryCallBack{

    }


}