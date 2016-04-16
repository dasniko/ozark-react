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
            <div className="card">
                <form className="bookForm card-content" onSubmit={this.handleSubmit}>
                    <div className="card-title">Add a new book:</div>
                    <input type="text" placeholder="Author" ref="author" className="form-control"/>
                    <input type="text" placeholder="Title" ref="title" className="form-control"/>
                    <button type="submit" className="btn btn-primary">Add book</button>
                </form>
            </div>
        );
    }
}
