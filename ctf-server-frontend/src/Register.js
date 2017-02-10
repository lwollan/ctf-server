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

        Datas.registerTeam(teamName.trim());
    };

    onInputChange = e => this.setState({ teamName: e.target.value });

    render() {
        return (
            <form onSubmit={ this.onSubmit }>
                <label htmlFor="team-name-input">Team name</label>
                <input id="team-name-input" onChange={ this.onInputChange } disabled={ this.state.isRegisteringTeam }/>
                <button type="submit" disabled={ this.state.isRegisteringTeam }>Submit</button>
            </form>
        )
    }
}
