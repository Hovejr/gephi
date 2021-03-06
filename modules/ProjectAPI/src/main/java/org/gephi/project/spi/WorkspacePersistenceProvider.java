/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2011 Gephi Consortium. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 3 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://gephi.org/about/legal/license-notice/
or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License files at
/cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 3, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 3] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 3 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 3 code and therefore, elected the GPL
Version 3 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):

Portions Copyrighted 2011 Gephi Consortium.
*/
package org.gephi.project.spi;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.gephi.project.api.Workspace;

/**
 * Interface modules implement to notify the system they can read/write part
 * of the .gephi project file to serialize states and data.
 * <h3>How saving a project works</h3>
 * <ol><li>The saving task is looking for all implementations of this interface and
 * asks to return an XML element that represents data for each workspace.</li>
 * <li>All of these elements are written in the .gephi project file.</li></ol>
 * <h3>How loading a project works</h3>
 * <ol><li>The loading task is looking for all implementations of this interface and
 * asks for the identifier returned by <code>getIdentifier()</code>.</li>
 * <li>When traversing the gephi project XML document it tries to match markups with
 * identifiers. When match, call this provider <code>readXML()</code> method
 * with the XML element.</li></ol>
 * 
 * <p>Thus this interface allows any module to serialize and deserialize its data
 * to gephi project files.</p>
 * 
 * <p>
 * In order to have your <code>WorkspacePersistenceProvider</code> called, 
 * you must annotate it with <code>@ServiceProvider(service = WorkspacePersistenceProvider.class, position = xy)</code>
 * </p>
 * <p>
 * The <code>position</code> parameter is optional but often useful when when you need other <code>WorkspacePersistenceProvider</code> data deserialized before yours.
 * </p>
 * 
 * @author Mathieu Bastian
 * @see Workspace
 */
public interface WorkspacePersistenceProvider {

    /**
     * <p>This is automatically called when saving a project file.</p>
     * <p>Your implementation must enclose all your data xml in a tag with the name provided in your <code>getIdentifier</code> method.</p>
     * @param writer XMLStreamWriter for xml serialization of this persistence provider data
     * @param workspace Current workspace being serialized
     */
    public void writeXML(XMLStreamWriter writer, Workspace workspace);

    /**
     * <p>This is automatically called when a start element with the tag name provided in your <code>getIdentifier</code> method.</p>
     * <p>Your implementation must detect the tag end element to stop reading.</p>
     * @param reader XMLStreamReader for deserialization of this persistence provider data previously serialized
     * @param workspace Current workspace being deserialized
     */
    public void readXML(XMLStreamReader reader, Workspace workspace);

    /**
     * Unique XML tag identifier for your <code>WorkspacePersistenceProvider</code>
     * @return Unique identifier describing your data
     */
    public String getIdentifier();
}
