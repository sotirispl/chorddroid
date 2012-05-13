package gr.aueb.mscis.protocol;


import gr.aueb.mscis.configuration.Config;
import gr.aueb.mscis.utils.Sha1;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * This class represents a Node's Id. The identity
 * of a node in our system is based on the IP and
 * the port that RemoteMessageDaemon is binded
 * @author lupin
 */
public class NodeIdentifier implements Serializable {

        private static final long serialVersionUID = 1L;
        private InetAddress addr;
        private int port;
        private String hash = null;
       
        public NodeIdentifier() {
                super();
                this.addr = getLocalIpAddress();
                /*pairnei to default port opws auto exei oristei sto config class*/
                this.port = Config.defaultPort;
        }
        public NodeIdentifier(int port) {
            this.port = port;
    }

        public NodeIdentifier(InetAddress addr, int port) {
                super();
                this.addr = addr;
                this.port = port;
        }

        public InetAddress getAddr() {
                return addr;
        }

        public void setAddr(InetAddress addr) {
                this.addr = addr;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }
       
        @Override
        public String toString() {
                return new String(addr.toString()+":"+ Integer.toString(port));
        }
       
        /**
         * Returns the hash based on the toString of the NodeIdentifier
         * @return
         */
        public String getHash(){
                if (hash == null) hash = Sha1.getHash(this.toString());
                return hash;
        }
       
        /**
         * Returns the local IP Address
         * @return
         */
        public InetAddress getLocalIpAddress() {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress;
                        }
                    }
                }
            } catch (SocketException ex) {
            }
            return null;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((addr == null) ? 0 : addr.hashCode());
                result = prime * result + ((hash == null) ? 0 : hash.hashCode());
                result = prime * result + port;
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                NodeIdentifier other = (NodeIdentifier) obj;
                if (addr == null) {
                        if (other.addr != null)
                                return false;
                } else if (!addr.equals(other.addr))
                        return false;
                if (hash == null) {
                        if (other.hash != null)
                                return false;
                } else if (!hash.equals(other.hash))
                        return false;
                if (port != other.port)
                        return false;
                return true;
        }
       
       


}

