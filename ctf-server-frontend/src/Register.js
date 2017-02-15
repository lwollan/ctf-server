import React, { Component } from 'react';
import Datas from './Datas';

export default class Register extends Component {

    state = {};

    onSubmit = e => {
        e.preventDefault();

        const { teamName, teamKey } = this.state;

        if (!teamName || !teamName.trim()) {
            return;
        }

        if (teamKey && teamKey.trim()) {
            Datas.loginTeam({
                team: teamName,
                'X-TEAM-KEY': teamKey
            });
        }

        this.setState({ saving: true, error: null });

        Datas.registerTeam(teamName.trim())
            .catch(err => this.setState({
                saving: false,
                teamName: '',
                error: err.status === 409 ? 'Team name already registered' : 'Oops'
            }));
    };

    onInputChange = e => this.setState({ [e.target.name]: e.target.value });

    render() {

        const { teamKey, error } = this.state;

        const label = teamKey && teamKey.trim() ? 'Login' : 'Register';

        return (
            <form className="block" onSubmit={ this.onSubmit }>
                <div className="input-group">
                    <label htmlFor="team-name-input" className="input-label">Team name</label>
                    <input id="team-name-input" name="teamName" className="input-text" onChange={ this.onInputChange } disabled={ this.state.saving }/>
                </div>
                <div className="input-group">
                    <label htmlFor="team-name-input" className="input-label">Team key (only for existing teams)</label>
                    <input id="team-key-input" name="teamKey" className="input-text" onChange={ this.onInputChange } disabled={ this.state.saving }/>
                </div>
                { error && (
                    <div className="input-group error">
                        { error }
                    </div>
                ) }
                <div className="actions">
                    <button type="submit" className="action">{ label }</button>
                </div>
            </form>
        )
    }
}
