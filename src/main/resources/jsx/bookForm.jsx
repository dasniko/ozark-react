import React from "react";

export default class BookForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleSubmit(e) {
        e.preventDefault();
        var author = this.refs.author.value.trim();
        var title = this.refs.title.value.trim();
        if (!author || !title) {
            return;
        }
        this.props.onBookSubmit({author: author, title: title});
        this.refs.author.value = '';
        this.refs.title.value = '';
    }

    render() {
        return (
            <form className="bookForm well" onSubmit={this.handleSubmit}>
                <h4>Add a new book:</h4>
                <input type="text" placeholder="Author" ref="author" className="form-control"/>
                <input type="text" placeholder="Title" ref="title" className="form-control"/>
                <button type="submit" className="btn btn-primary">Add book</button>
            </form>
        );
    }
}
