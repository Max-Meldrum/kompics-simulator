package se.sics.kompics.simulator.core.impl;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import se.sics.kompics.simulator.network.identifier.Identifier;
import se.sics.kompics.simulator.util.SimulationContext;

/**
 * @author Alex Ormenisan <aaor@sics.se>
 */
public class SimulationContextImpl implements SimulationContext {

    private final SimulatorMngrComp simMngr;
    private final Random rand;
    private final Set<Identifier> aliveNodes = new HashSet<>();
    private final Set<Identifier> deadNodes = new HashSet<>();
    private final Map<String, Object> otherContext = new HashMap<>();
    private boolean terminated;

    public SimulationContextImpl(SimulatorMngrComp simMngr, Random rand) {
        this.simMngr = simMngr;
        this.rand = rand;
        terminated = false;
    }

    public Random getRand() {
        return rand;
    }

    @Override
    public Set<Identifier> getAliveNodes() {
        return aliveNodes;
    }

    public void startNode(Identifier id) {
        aliveNodes.add(id);
    }

    @Override
    public Set<Identifier> getDeadNodes() {
        return deadNodes;
    }

    public void killNode(Identifier id) {
        aliveNodes.remove(id);
        deadNodes.add(id);
    }

    @Override
    public void terminate() {
        if (!terminated) {
            terminated = true;
            simMngr.terminate();
        }
    }

    public boolean terminated() {
        return terminated;
    }

    /**
     * @param identifier
     * @param obj
     * @return false if registration could not happen. Possible causes: 1. there
     * is already an object registered with that identifier
     */
    @Override
    public boolean register(String identifier, Object obj) {
        if (otherContext.containsKey(identifier)) {
            return false;
        }
        otherContext.put(identifier, obj);
        return true;
    }

    @Override
    public Object get(String identifier) {
        return otherContext.get(identifier);
    }
}
