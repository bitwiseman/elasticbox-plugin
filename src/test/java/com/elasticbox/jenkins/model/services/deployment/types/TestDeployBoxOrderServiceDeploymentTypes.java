/*
 *
 *  ElasticBox Confidential
 *  Copyright (c) 2016 All Right Reserved, ElasticBox Inc.
 *
 *  NOTICE:  All information contained herein is, and remains the property
 *  of ElasticBox. The intellectual and technical concepts contained herein are
 *  proprietary and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination of this
 *  information or reproduction of this material is strictly forbidden unless prior
 *  written permission is obtained from ElasticBox.
 *
 */

package com.elasticbox.jenkins.model.services.deployment.types;

import com.elasticbox.APIClient;
import com.elasticbox.jenkins.UnitTestingUtils;
import com.elasticbox.jenkins.model.repository.BoxRepository;
import com.elasticbox.jenkins.model.repository.api.BoxRepositoryAPIImpl;
import com.elasticbox.jenkins.model.repository.error.RepositoryException;
import com.elasticbox.jenkins.model.services.DeployBoxOrderServiceImpl;
import com.elasticbox.jenkins.model.services.error.ServiceException;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by serna on 1/13/16.
 */
public class TestDeployBoxOrderServiceDeploymentTypes {

    @Test
    public void testPolicyBasedDeploymentType() throws IOException, RepositoryException, ServiceException {

        testDeploymentType(UnitTestingUtils.getFakeScriptBox(), PolicyBasedDeploymentTypeHandler.class);

    }

    @Test
    public void testApplicationBoxBasedDeploymentType() throws IOException, RepositoryException, ServiceException {

        testDeploymentType(UnitTestingUtils.getFakeApplicationBox(), ApplicationBoxDeploymentTypeHandler.class);

    }

    @Test
    public void testCloudFormationManagedDeploymentType() throws IOException, RepositoryException, ServiceException {

        testDeploymentType(UnitTestingUtils.getFakeCloudFormationManagedBox(), CloudFormationManagedDeploymentTypeHandler.class);

    }

    private void testDeploymentType(JSONObject fakeBox, Class deploymentTypeClass) throws IOException, ServiceException {

        final APIClient api = mock(APIClient.class);
        when(api.getBox(fakeBox.getString("id"))).thenReturn(fakeBox);

        final BoxRepository boxRepository = new BoxRepositoryAPIImpl(api);

        final DeployBoxOrderServiceImpl deployBoxOrderService = new DeployBoxOrderServiceImpl(boxRepository);

        final DeploymentTypeHandler deploymentTypeHandler = deployBoxOrderService.deploymentType(fakeBox.getString("id"));

        assertTrue("Deployment type should be "+deploymentTypeClass.getSimpleName(), deploymentTypeHandler.getClass() == deploymentTypeClass);

    }

}
