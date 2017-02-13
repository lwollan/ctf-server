import React, { Component } from 'react';
import Datas from './Datas';

export default class Register extends Component {

    state = {};

    onSubmit = e => {
        e.preventDefault();

        const { teamName } = this.state;

        if (!teamName || !teamName.trim()) {
            return;
        }

        this.setState({ saving: true });

        Datas.registerTeam(teamName.trim())
            .catch(() => this.setState({ saving: false, teamName: '' }));
    };

    onInputChange = e => this.setState({ teamName: e.target.value });

    render() {
        return (
            <form className="block" onSubmit={ this.onSubmit }>
                <label htmlFor="team-name-input">Team name</label>
                <input id="team-name-input" onChange={ this.onInputChange } disabled={ this.state.saving }/>
                <button type="submit" disabled={ this.state.saving }>Submit</button>
            </form>
        )
    }
}
