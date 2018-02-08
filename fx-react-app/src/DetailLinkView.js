import React from 'react';
import { graphql } from 'react-apollo';
import gql from 'graphql-tag';

const query = gql`
query DetailLinkView($id: ID!) {
    oneLink(id: $id) {
        id
        url
        description
    }
}
`;

const queryOptions = {
    options: (props) => ({
        variables: {
            id: props.match.params.id,
        },
    }),
};

class DetailLinkView extends React.Component {
    render() {
        let { data } = this.props;
        if (data.loading) {
            return <div>Loading ...</div>
        }
        let { oneLink } = data;
        return (
            <ul>
                <li>{oneLink.id}</li>
                <li>{oneLink.url}</li>
                <li>{oneLink.description}</li>
            </ul>
        );
    }
}
export default graphql(query, queryOptions)(DetailLinkView);