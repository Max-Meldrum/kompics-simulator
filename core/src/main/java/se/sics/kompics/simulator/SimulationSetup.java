/*
 * This file is part of the Kompics Simulator.
 *
 * Copyright (C) 2009 Swedish Institute of Computer Science (SICS) 
 * Copyright (C) 2009 Royal Institute of Technology (KTH)
 *
 * This program is free software; you can redistribute it and/or
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
package se.sics.kompics.simulator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import se.sics.kompics.simutil.msg.impl.BasicAddress;

/**
 * @author Alex Ormenisan <aaor@kth.se>
 */
public class SimulationSetup {
    public static final String GLOBAL_VIEW_ADDRESS = "simulation.globalview";
    public static final BasicAddress globalViewAddress;
    static {
        try {
            globalViewAddress = new BasicAddress(InetAddress.getByName("0.0.0.0"), 0, null);
        } catch (UnknownHostException ex) {
            throw new RuntimeException("global view address error");
        }
    }
}
