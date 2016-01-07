package org.zxc.zing.common.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by xuanchen.zhao on 15-12-15.
 */
public class RemoteRequest implements Serializable{

    private String requestId;
    private String serviceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RemoteRequest{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", parameterTypes=").append(Arrays.toString(parameterTypes));
        sb.append(", arguments=").append(Arrays.toString(arguments));
        sb.append('}');
        return sb.toString();
    }
}
