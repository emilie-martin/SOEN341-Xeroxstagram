import React from 'react'
import './EditProfile.scss'

const EditProfile = () => {

    return (
        <div className="edit-profile">
            <form className="edit-profile-form">
                <div>
                    <label>Display Name</label>
                    <input type="text" name="displayName"></input>
                </div>
                <br/>
                <div>
                    <label>Bio</label>
                    <textarea name="bio" className="edit-profile-textarea"></textarea>
                </div>

                <br />
                <div>
                    <label>Email</label>
                    <input type="text" name="email"></input>
                </div>
                <div className="edit-profile-button">
                    <button className="btn">Submit</button>
                </div>
                <br />

            </form>
        </div>
    )
}

export default EditProfile
