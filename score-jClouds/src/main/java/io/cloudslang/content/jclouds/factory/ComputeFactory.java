package io.cloudslang.content.jclouds.factory;

import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.entities.inputs.CommonInputs;
import io.cloudslang.content.jclouds.services.ComputeService;
import io.cloudslang.content.jclouds.services.impl.AmazonComputeService;
import io.cloudslang.content.jclouds.services.impl.ComputeServiceImpl;
import io.cloudslang.content.jclouds.services.impl.OpenstackComputeService;

/**
 * Created by persdana on 5/27/2015.
 */
public class ComputeFactory {
    public static ComputeService getComputeService(CommonInputs commonInputs) throws Exception {
        ComputeService computeService;
        switch (commonInputs.getProvider().toLowerCase()) {
            case Constants.Providers.AMAZON:
                computeService = new AmazonComputeService(
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
                break;
            case Constants.Providers.OPENSTACK:
                computeService = new OpenstackComputeService(
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
                break;
            default:
                computeService = new ComputeServiceImpl(
                        commonInputs.getProvider(),
                        commonInputs.getEndpoint(),
                        commonInputs.getIdentity(),
                        commonInputs.getCredential(),
                        commonInputs.getProxyHost(),
                        commonInputs.getProxyPort());
        }

        return computeService;
    }
}