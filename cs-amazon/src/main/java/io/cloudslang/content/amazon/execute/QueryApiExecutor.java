/*******************************************************************************
 * (c) Copyright 2016 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.content.amazon.execute;

import io.cloudslang.content.amazon.entities.aws.AuthorizationHeader;
import io.cloudslang.content.amazon.entities.inputs.CommonInputs;
import io.cloudslang.content.amazon.entities.inputs.InputsWrapper;
import io.cloudslang.content.amazon.factory.HeadersMapBuilder;
import io.cloudslang.content.amazon.factory.InputsWrapperBuilder;
import io.cloudslang.content.amazon.factory.ParamsMapBuilder;
import io.cloudslang.content.amazon.services.AmazonSignatureService;
import io.cloudslang.content.amazon.utils.InputsUtil;
import io.cloudslang.content.httpclient.CSHttpClient;

import java.net.MalformedURLException;
import java.security.SignatureException;
import java.util.Map;

import static io.cloudslang.content.amazon.entities.constants.Constants.Miscellaneous.AMPERSAND;
import static io.cloudslang.content.amazon.entities.constants.Constants.Miscellaneous.EQUAL;
import static io.cloudslang.content.amazon.utils.OutputsUtil.getValidResponse;

/**
 * Created by Mihai Tusa.
 * 9/6/2016.
 */
public class QueryApiExecutor {
    @SafeVarargs
    public final <T> Map<String, String> execute(CommonInputs commonInputs, T... builders) throws Exception {
        InputsWrapper inputs = InputsWrapperBuilder.getWrapper(commonInputs, builders);

        Map<String, String> queryParamsMap = ParamsMapBuilder.getParamsMap(inputs);
        Map<String, String> headersMap = HeadersMapBuilder.getHeadersMap(inputs);

        setQueryApiParams(inputs, queryParamsMap);
        setQueryApiHeaders(inputs, headersMap, queryParamsMap);

        Map<String, String> awsResponse = new CSHttpClient().execute(inputs.getHttpClientInputs());

        return getValidResponse(awsResponse);
    }

    void setQueryApiHeaders(InputsWrapper inputs, Map<String, String> headersMap, Map<String, String> queryParamsMap)
            throws SignatureException, MalformedURLException {
        AuthorizationHeader signedHeaders = new AmazonSignatureService().signRequestHeaders(inputs, headersMap, queryParamsMap);
        inputs.getHttpClientInputs().setHeaders(signedHeaders.getAuthorizationHeader());
    }

    private void setQueryApiParams(InputsWrapper inputs, Map<String, String> queryParamsMap) {
        String queryParamsString = InputsUtil.getHeadersOrParamsString(queryParamsMap, EQUAL, AMPERSAND, true);
        inputs.getHttpClientInputs().setQueryParams(queryParamsString);
    }
}