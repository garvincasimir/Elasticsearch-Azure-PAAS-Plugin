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
public class ElasticsearchNode {

    //Gson
    public String Ip;
    public int Port;
    public String Status;
    public String NodeName;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String name) {
        this.NodeName = name;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        this.Ip = ip;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        this.Port = port;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Instance{");
        sb.append("ip='").append(Ip).append('\'');
        sb.append("port='").append(Port).append('\'');
        sb.append(", status=").append(Status);
        sb.append(", nodeName='").append(NodeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
