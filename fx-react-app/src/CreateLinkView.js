import React from 'react';
import { graphql } from 'react-apollo';
import gql from 'graphql-tag';

const query = gql`
query AllLinksViewQuery {
    allLinks {
        id
        url
        description
    }
}
`;
const query2 = gql`
query DetailLinkView($id: ID!) {
    oneLink(id: $id) {
        id
        url
        description
    }
}
`;
const mutation = gql`
mutation CreateLinkView_createLink($url: String!, $desc: String!) {
    createLink(url: $url, description: $desc) {
      id
      url
      description
    }
  }
`;

class CreateLinkView extends React.Component {
    handleSubmit(e) {
        e.preventDefault();
        let formData = new FormData(this.form);
        this.props
        .mutate({
            variables: {
                url: formData.get('url'),
                desc: formData.get('description'),
            },
            update: (proxy, { data: { createLink } }) => {
                const data = proxy.readQuery({ query: query });
                data.allLinks.push(createLink);
                proxy.writeQuery({ query: query, data: data})

                proxy.writeQuery({
                    query: query2,
                    variables: {
                        id: createLink.id,
                    },
                    data: {oneLink: createLink},
                });
            },
        })
        .then(res => {
            let {data} = res;
            this.props.history.push('/links/' + data.createLink.id);
        })
        .catch(err => console.log(err));
    }
    render() {
        return (
            <div>
                <h1>create link</h1>
                <form
                    ref={ref => (this.form = ref)}
                    onSubmit={e => this.handleSubmit(e)}>
                    <textarea name="url" />
                    <textarea name="description" />
                    <button type="submit">Submit</button>
                </form>
            </div>
        );
    }
}

export default graphql(mutation)(CreateLinkView);