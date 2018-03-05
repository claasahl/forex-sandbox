import gql from "graphql-tag";

export default gql`
  query BrokerQuery($brokerId: ID!) {
    broker(id: $brokerId) {
      id
      name
      symbols {
        id
        name
        duration
      }
    }
  }
`;
