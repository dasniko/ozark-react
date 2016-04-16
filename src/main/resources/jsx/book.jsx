import React from "react";

export default class Book extends React.Component {
    render() {
        return (
            <div className="book">
                <div className="divider"></div>
                <h4>{this.props.author}</h4>
                <div className="flow-text">{this.props.title}</div>
            </div>
        );
    }
}
