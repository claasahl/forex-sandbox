import React from 'react';
import { graphql } from 'react-apollo';
import gql from 'graphql-tag';

const mutation = gql`
mutation CreateLinkView_createLink($url: String!, $desc: String!) {
    createLink(url: $url, description: $desc) {
      id
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
        })
        .then(res => {
            let {data} = res;
            window.location.replace('/links/' + data.createLink.id);
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