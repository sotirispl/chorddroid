package gr.aueb.mscis.protocol;

import java.io.Serializable;

/**
 * This class is a stack of Serializable Objects
 * that represents in total, a message between two nodes
 * that triggers a method call on the recipient {@code RemoteMessageDaemon}.
 * {@code RemoteMessage} objects are send from the {@code RemoteMessageAgent}.
 * @author lupin
 *
 */
public class RemoteMessage implements Serializable {

        private static final long serialVersionUID = 1L;
        // payload may be a key or a node or another serializable obj
        private Object payload;
        private ProtocolType protocolType;
        // this id is used for direct message reply
        private NodeIdentifier nodeId;

        public RemoteMessage(ProtocolType pt, Object payload, NodeIdentifier nodeId) {
                this.payload = payload;
                this.protocolType = pt;
                this.nodeId = nodeId;
        }

        public Object getPayload() {
                return payload;
        }

        public void setPayload(Object payload) {
                this.payload = payload;
        }

        public ProtocolType getProtocolType() {
                return protocolType;
        }

        public void setProtocolType(ProtocolType protocolType) {
                this.protocolType = protocolType;
        }

        public NodeIdentifier getNodeId() {
                return nodeId;
        }

        public void setNodeId(NodeIdentifier nodeId) {
                this.nodeId = nodeId;
        }
}
