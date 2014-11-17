/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.garvincasimir.elasticsearch.azureruntime;

/**
 * Define an Azure Instance
 */
public class RuntimeInstance {

    private String ip;
    private String port;
    private String status;
    private String nodeName;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String name) {
        this.nodeName = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuntimeInstance instance = (RuntimeInstance) o;

        if (nodeName != null ? !nodeName.equals(instance.nodeName) : instance.nodeName != null) return false;
        if (ip != null ? !ip.equals(instance.ip) : instance.ip != null) return false;
        if (port != null ? !port.equals(instance.port) : instance.port != null) return false;
        if (status != instance.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;

        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (nodeName != null ? nodeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Instance{");
        sb.append("ip='").append(ip).append('\'');
        sb.append("port='").append(port).append('\'');
        sb.append(", status=").append(status);
        sb.append(", nodeName='").append(nodeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
