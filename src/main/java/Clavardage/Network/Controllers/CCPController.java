package Clavardage.Network.Controllers;

import Clavardage.Models.LocalUser;
import Clavardage.Models.User;
import Clavardage.Network.Models.CCPPacket;
import Clavardage.Network.SocketProtocols.CCPsocket;
import Clavardage.Network.Models.Address;
import Clavardage.Network.Types.CCPPacketType;

public class CCPController {

    public CCPController() {}

    public void sendDiscovery() { //TODO: Improve Discovery sending using Multicast (Less trafic and better efficiency)
        CCPPacket discover = new CCPPacket(CCPPacketType.DISCOVER, LocalUser.getInstance());
        CCPsocket sock = new CCPsocket();
        sock.send(discover);
        sock.close();
        System.out.println("Sending discovery message : "+ discover);
    }

    public void sendReplyTo(User remoteUser) {
        CCPPacket reply = new CCPPacket(CCPPacketType.REPLY, LocalUser.getInstance());
        reply.setDestAddr(remoteUser.getAddr());
        System.out.println("Sending REPLY : " + reply);
        CCPsocket sock = new CCPsocket();
        sock.send(reply);
        sock.close();
    }
}