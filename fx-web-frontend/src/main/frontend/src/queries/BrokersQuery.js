import gql from "graphql-tag";

export default gql`
  query BrokersQuery($brokerId: ID) {
    brokers(filter: { brokerId: $brokerId }) {
      id
      name
      symbols {
        name
        duration
      }
    }
  }
`;
