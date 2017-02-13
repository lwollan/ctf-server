import React, { Component } from 'react';

import Datas from './Datas';

export default class Flag extends Component {

    state = {};

    postFlag = e => {
        e.preventDefault();
        const { value } = this.inputNode;
        const { flag } = this.props;

        if (!value || !value.trim()) {
            return;
        }

        Datas.postFlag(flag.get('flagId'), value)
            .catch(() => this.setState({ incorrect: true }));
    };

    render() {
        const { flag } = this.props;
        const { incorrect } = this.state;

        return (
            <form className={ `flag ${incorrect ? 'incorrect' : ''}` } onSubmit={ this.postFlag }>
                <header>{ flag.get('flagName') }</header>
                <div className="flag-content">
                    <input type="text" className="flag-answer" ref={ node => this.inputNode = node }/>
                    <div className="flag-actions">
                        <button className="flag-action" type="submit">Submit</button>
                    </div>
                </div>
            </form>
        );
    }
}