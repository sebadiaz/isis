/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.runtimes.dflt.remoting.marshalling.xstream.server;

import java.util.List;

import org.apache.isis.runtimes.dflt.remoting.common.protocol.ObjectEncoderDecoder;
import org.apache.isis.runtimes.dflt.remoting.transport.sockets.server.SocketsViewerAbstract;
import org.apache.isis.runtimes.dflt.remoting.transport.sockets.server.SocketsViewerInstallerAbstract;
import org.apache.isis.runtimes.dflt.runtime.Isis;
import org.apache.isis.runtimes.dflt.runtime.viewer.IsisViewer;

/**
 * Implementation of a {@link IsisViewer} providing the ability to run from server as a {@link Isis command line}
 * application.
 * 
 * <p>
 * To run, use the <tt>--viewer xstream-sockets</tt> flag. The client-side should run using
 * <tt>--connector xstream-sockets</tt> flag.
 */
public class XStreamOverSocketsViewerInstaller extends SocketsViewerInstallerAbstract {

    public XStreamOverSocketsViewerInstaller() {
        super("xstream-sockets");
    }

    @Override
    protected void addConfigurationResources(final List<String> configurationResources) {
        super.addConfigurationResources(configurationResources);
        // TODO: this (small) hack is because we don't load up the Protocol (Marshaller)
        // and Transport using the installers.
        configurationResources.add("protocol.properties");
        configurationResources.add("protocol_xstream.properties");
        configurationResources.add("transport.properties");
        configurationResources.add("transport_sockets.properties");
    }

    @Override
    protected SocketsViewerAbstract createSocketsViewer(final ObjectEncoderDecoder objectEncoderDecoder) {
        return new XStreamOverSocketsViewer(objectEncoderDecoder);
    }
}