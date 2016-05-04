package io.cloudslang.content.jclouds.factory;

import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.entities.inputs.CommonInputs;
import io.cloudslang.content.jclouds.services.ImageService;
import io.cloudslang.content.jclouds.services.impl.imagesImpl.AmazonImageService;
import io.cloudslang.content.jclouds.services.impl.imagesImpl.OpenStackImageService;

/**
 * Created by Mihai Tusa.
 * 5/4/2016.
 */
public class ImageFactory {
    public static ImageService getImageService(CommonInputs commonInputs) throws Exception {
        ImageService imageService;
        switch (commonInputs.getProvider().toLowerCase()) {
            case Constants.Providers.AMAZON:
                imageService = new AmazonImageService(
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
                break;
            case Constants.Providers.OPENSTACK:
                imageService = new OpenStackImageService(
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
                break;
            default:
                imageService = new AmazonImageService(
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
        }

        return imageService;
    }
}