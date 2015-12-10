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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import se.sics.kompics.network.Address;
import se.sics.kompics.network.Msg;
import se.sics.kompics.simulator.network.NetworkModel;
import se.sics.kompics.simutil.identifiable.Identifiable;
import se.sics.kompics.simutil.identifiable.Identifier;

/**
 * The <code>KingLatencyMap</code> class.
 *
 * @author Cosmin Arad <cosmin@sics.se>
 * @author Tallat Shafaat <tallat@sics.se>
 * @version $Id$
 */
public final class KingLatencyModel implements NetworkModel {

    private final Random random;

    private long[] repeatedDiagonal;

    public KingLatencyModel() {
        random = new Random();
        initRepeatedDiagonal(random);
    }

    public KingLatencyModel(long seed) {
        random = new Random(seed);
        initRepeatedDiagonal(random);
    }

    private void initRepeatedDiagonal(Random r) {
        repeatedDiagonal = new long[KingMatrix.SIZE];

        for (int i = 0; i < repeatedDiagonal.length; i++) {
            int j = r.nextInt(KingMatrix.SIZE);
            int k = r.nextInt(KingMatrix.SIZE);
            while (k == j) {
                k = r.nextInt(KingMatrix.SIZE);
            }
            repeatedDiagonal[i] = KingMatrix.KING[j][k];
        }
    }

    @Override
    public long getLatencyMs(Msg message) {
        int s = addressToInt(message.getHeader().getSource());
        int d = addressToInt(message.getHeader().getDestination());

        if (s == d) {
            Identifier srcId = ((Identifiable) message.getHeader().getSource()).getId();
            Identifier dstId = ((Identifiable) message.getHeader().getDestination()).getId();
            if (srcId.equals(dstId)) {
                return repeatedDiagonal[s];
            }
        }

        return KingMatrix.KING[s][d];
    }

    private final int addressToInt(Address address) {
        Identifier id = ((Identifiable)address).getId();
        return id.partition(KingMatrix.SIZE);
    }

    private static final class KingMatrix {

        public static final int KING[][];
        public static final int SIZE = 1740;

        static {
            int king[][];
            try(ObjectInputStream ois = new ObjectInputStream(KingMatrix.class.getResourceAsStream("KingMatrix.data"))) {
                king = (int[][]) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                king = null;
            }
            KING = king;
        }
    }
}
