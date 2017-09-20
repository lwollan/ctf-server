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

    getStatusClass() {
        const { flag } = this.props;

        if (flag.get('flagAnswered') === 'true') {
            return ('answered');
        }

        if (this.state.incorrect) {
            return 'incorrect';
        }

        return '';
    }

    render() {
        const { flag } = this.props;
        const answered = flag.get('flagAnswered') === 'true';

        return (
            <form className={ `flag ${this.getStatusClass()}` } onSubmit={ this.postFlag }>
                <header>{ flag.get('flagName') }</header>
                <i>{ flag.get('flagDescription')}</i>
                { !answered && <div className="flag-content">
                    <input type="text" className="input-text" disabled={ answered } ref={ node => this.inputNode = node }/>
                    <div className="actions">
                        <button className="action" type="submit">Submit</button>
                    </div>
                </div> }
            </form>
        );
    }
}