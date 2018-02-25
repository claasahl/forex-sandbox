import gql from "graphql-tag";

export default gql`
  {
    brokers {
      id
      name
      symbols {
        name
        duration
      }
    }
  }
`;
