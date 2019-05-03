/**
 * This file is part of the Kompics P2P Framework.
 *
 * Copyright (C) 2009 Swedish Institute of Computer Science (SICS) Copyright (C)
 * 2009 Royal Institute of Technology (KTH)
 *
 * Kompics is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package se.sics.kompics.simulator.stochastic.events;

/**
 * The <code>StochasticSimulationTerminatedEvent</code> class.
 *
 * @author Cosmin Arad <cosmin@sics.se>
 * @version $Id$
 */
public final class StochasticSimulationTerminatedEvent extends StochasticSimulatorEvent {

    private static final long serialVersionUID = 5884731040528351273L;

    private final long delay;
    private int waitFor;
    private final boolean relativeTime;

    public StochasticSimulationTerminatedEvent(long time, int waitFor, boolean relativeTime) {
        super(time);
        delay = time;
        this.relativeTime = relativeTime;
        this.waitFor = waitFor;
    }

    public boolean isRelativeTime() {
        return relativeTime;
    }

    public final boolean shouldTerminateNow() {
        waitFor--;
        return waitFor <= 0 ? true : false;
    }

    @Override
    public final void setTime(long time) {
        time += delay;
        if (time > getTime()) {
            // only move time forward
            super.setTime(time);
        }
    }

    public long getDelay() {
        return delay;
    }
}
