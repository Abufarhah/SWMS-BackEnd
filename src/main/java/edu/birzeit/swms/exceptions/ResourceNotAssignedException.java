package edu.birzeit.swms.exceptions;

public class ResourceNotAssignedException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String resourceName;
    private int resourceId;
    private String assignedResourceName;
    private int assignedResourceId;

    public ResourceNotAssignedException() {
        super();
    }

    public ResourceNotAssignedException(String resourceName, int resourceId, String assignedResourceName, int assignedResourceId) {
        super(String.format("%s with id: '%s' is not assigned to %s with id: %s", assignedResourceName, assignedResourceId, resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.assignedResourceName = assignedResourceName;
        this.assignedResourceId = assignedResourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getAssignedResourceName() {
        return assignedResourceName;
    }

    public void setAssignedResourceName(String assignedResourceName) {
        this.assignedResourceName = assignedResourceName;
    }

    public int getAssignedResourceId() {
        return assignedResourceId;
    }

    public void setAssignedResourceId(int assignedResourceId) {
        this.assignedResourceId = assignedResourceId;
    }

    @Override
    public String toString() {
        return super.getMessage();
    }

}

