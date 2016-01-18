package com.elasticbox.jenkins.model.services;

import com.elasticbox.jenkins.model.box.*;
import com.elasticbox.jenkins.model.box.order.DeployBoxOrderResult;
import com.elasticbox.jenkins.model.box.policy.PolicyBox;
import com.elasticbox.jenkins.model.repository.BoxRepository;
import com.elasticbox.jenkins.model.repository.error.RepositoryException;
import com.elasticbox.jenkins.model.services.deployment.types.DeploymentTypeHandler;
import com.elasticbox.jenkins.model.services.deployment.types.DeploymentTypeDirector;
import com.elasticbox.jenkins.model.services.error.ServiceException;

import java.util.List;

/**
 * This is how we decided to encapsulate the business logic. So, this service model the deploy box
 * user case.
 */
public class DeployBoxOrderServiceImpl implements DeployBoxOrderService {

    private BoxRepository boxRepository;

    public DeployBoxOrderServiceImpl(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Override
    public DeploymentTypeHandler deploymentType(String boxToDeploy) throws ServiceException {

        try {
            final AbstractBox box = boxRepository.getBox(boxToDeploy);

            final DeploymentTypeHandler deploymentTypeHandler = new DeploymentTypeDirector().getDeploymentType(box);
            return deploymentTypeHandler;

        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ServiceException("Impossible to retrieve box: "+boxToDeploy);
        }
    }


    @Override
    public DeployBoxOrderResult<List<PolicyBox>> deploymentPolicies(String workspace, String boxToDeploy) throws ServiceException {

        try {
            final AbstractBox box = boxRepository.getBox(boxToDeploy);
            final List<PolicyBox> policies = new DeploymentTypeDirector().getPolicies(boxRepository, workspace, box);
            return new DeployBoxOrderResult<List<PolicyBox>>(policies);

        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ServiceException("Impossible to get policies for workspace: "+workspace+", box: "+boxToDeploy);
        }

    }




}
