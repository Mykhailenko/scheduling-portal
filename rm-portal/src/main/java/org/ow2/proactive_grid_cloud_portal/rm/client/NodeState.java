/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.rm.client;

/**
 * Enumeration of all states of a RMNode :<BR>
 * -deploying: node deployment is on going. <BR>
 * -lost: node deployment failed. <BR>
 * -free: node is ready to perform a task.<BR>
 * -configuring: node has been added to the RM but is in configuring state, not ready to perform a task.<BR>
 * -busy: node is executing a task.<BR>
 * -to be removed: node is busy and have to be removed at the end of its current task.<BR>
 * -down: node is broken, and not anymore able to perform tasks.<BR>
 */
public enum NodeState {
    /**
     * a node that can be provided to a RM user, and able to perform a task
     */
    FREE("Free"),
    /**
     * a node provided to a RM user.
     */
    BUSY("Busy"),
    /**
     * a node that has been detected down.
     */
    DOWN("Down"),
    /**
     * a busy node which must be removed from resource manager when the user
     * will give back the node.
     */
    TO_BE_REMOVED("To_be_removed"),
    /**
     * a node for which one the deployment process has been triggered but which
     * is not registered yet.
     */
    DEPLOYING("Deploying"),
    /**
     * a node for which one the deployment process failed
     */
    LOST("Lost"),
    /**
     * a node cannot be provided to a RM user, it is under configuration
     */
    CONFIGURING("Configuring"),

    /**
     * a node that was removed completely
     */
    REMOVED("Removed");


    private String desc;

    /**
     * Constructor
     * @param desc state to specify.
     */
    NodeState(String desc) {
        this.desc = desc;
    }

    /**
     * @param value value returned by NodeState.toString()
     * @return enum instance corresponding the String representation provided
     * @throws IllegalArgumentException provided value is no good
     */
    public static NodeState parse(String value) {
        if (value.equalsIgnoreCase(FREE.toString()))
            return NodeState.FREE;
        else if (value.equalsIgnoreCase(BUSY.toString()))
            return NodeState.BUSY;
        else if (value.equalsIgnoreCase(DOWN.toString()))
            return NodeState.DOWN;
        else if (value.equalsIgnoreCase(TO_BE_REMOVED.toString()))
            return NodeState.TO_BE_REMOVED;
        else if (value.equalsIgnoreCase(DEPLOYING.toString()))
            return NodeState.DEPLOYING;
        else if (value.equalsIgnoreCase(LOST.toString()))
            return NodeState.LOST;
        else if (value.equalsIgnoreCase(CONFIGURING.toString()))
            return NodeState.CONFIGURING;
        else if (value.equalsIgnoreCase(REMOVED.toString()))
            return NodeState.REMOVED;
        else
            throw new IllegalArgumentException("'" + value + "' is not a valid NodeState");
    }

    /**
     * Returns a string representation of the state.
     *
     * @return String representation of the state.
     */
    @Override
    public String toString() {
        return desc;
    }

}
