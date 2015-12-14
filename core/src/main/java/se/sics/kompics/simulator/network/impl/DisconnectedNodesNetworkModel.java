/*
 * Copyright (C) 2009 Swedish Institute of Computer Science (SICS) Copyright (C)
 * 2009 Royal Institute of Technology (KTH)
 *
 * GVoD is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package se.sics.kompics.simulator.network.impl;

import java.util.Set;
import se.sics.kompics.network.Msg;
import se.sics.kompics.simulator.network.NetworkModel;
import se.sics.kompics.simulator.network.identifier.Identifier;
import se.sics.kompics.simulator.network.identifier.IdentifierExtractor;

/**
 * @author Alex Ormenisan <aaor@sics.se>
 */
public class DisconnectedNodesNetworkModel implements NetworkModel {

    private final IdentifierExtractor idE;
    private final NetworkModel baseNM;
    private final Set<Identifier> disconnectedNodes;

    public DisconnectedNodesNetworkModel(IdentifierExtractor idE, NetworkModel baseNM, Set<Identifier> disconnectedNodes) {
        this.idE = idE;
        this.baseNM = baseNM;
        this.disconnectedNodes = disconnectedNodes;
    }

    @Override
    public long getLatencyMs(Msg message) {
        Identifier srcId = idE.extract(message.getHeader().getSource());
        Identifier dstId = idE.extract(message.getHeader().getDestination());
        if (disconnectedNodes.contains(srcId) || disconnectedNodes.contains(dstId))  {
            return -1;
        }
        return baseNM.getLatencyMs(message);
    }

    @Override
    public String toString() {
        return "DisconnectedNodes NetworkModel";
    }
}
