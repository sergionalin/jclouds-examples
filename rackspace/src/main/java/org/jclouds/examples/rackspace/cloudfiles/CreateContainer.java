/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.examples.rackspace.cloudfiles;

import static org.jclouds.examples.rackspace.cloudfiles.Constants.CONTAINER;
import static org.jclouds.examples.rackspace.cloudfiles.Constants.PROVIDER;

import java.io.Closeable;
import java.io.IOException;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.swift.CommonSwiftClient;
import org.jclouds.openstack.swift.options.CreateContainerOptions;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Closeables;

/**
 * Create a Cloud Files container with some metadata associated with it.
 *  
 * @author Everett Toews
 * @author Jeremy Daggett
 */
public class CreateContainer implements Closeable {
   private final CommonSwiftClient swift;

   /**
    * To get a username and API key see http://www.jclouds.org/documentation/quickstart/rackspace/
    * 
    * The first argument (args[0]) must be your username
    * The second argument (args[1]) must be your API key
    */
   public static void main(String[] args) throws IOException {
      CreateContainer createContainer = new CreateContainer(args[0], args[1]);

      try {
         createContainer.createContainer();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         createContainer.close();
      }
   }

   public CreateContainer(String username, String apiKey) {
      swift = ContextBuilder.newBuilder(PROVIDER)
            .credentials(username, apiKey)
            .buildApi(CommonSwiftClient.class);
   }

   private void createContainer() {
      System.out.format("Create Container%n");

      CreateContainerOptions options = CreateContainerOptions.Builder
            .withMetadata(ImmutableMap.of("key1", "value1", "key2", "value2"));

      swift.createContainer(CONTAINER, options);

      System.out.format("  %s%n", CONTAINER);
   }

   /**
    * Always close your service when you're done with it.
    */
   public void close() throws IOException {
      Closeables.close(swift, true);
   }
}
