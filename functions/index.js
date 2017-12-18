const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.setUsername = functions.database.ref('/users/{userID}/displayname').onWrite(event => {
	const original = event.data.val();
	console.log('lowercasing', event.params.userID, original);
	const lowercaseUsername = original.toLowerCase();
	return event.data.ref.parent.child('username').set(lowercaseUsername);
});

exports.setFlatUsername = functions.database.ref('/users/{userID}/username').onWrite(event => {
    const original = event.data.val();
	const userID = event.params.userID;
	return event.data.ref.parent.parent.parent.child('usernames').child(original).set(userID);
});

exports.imageListSyncToUser = functions.database.ref('/images/{imageID}').onWrite(event => {
	const original = event.data.val();
	const imageID = event.params.imageID;
	const UID = event.data.child('owner_id').val();
	const imageURL = event.data.child('url').val();
	return event.data.ref.parent.parent.child('users').child(UID).child('imagesowned').child(imageID).set(imageURL);
});

exports.countUpvotes = functions.database.ref('/images/{imageID}/upvotes').onWrite(event => {
 	return event.data.ref.parent.child('upvote_count').set(event.data.numChildren()).then;
});

exports.countDownvotes = functions.database.ref('/images/{imageID}/downvotes').onWrite(event => {
 	return event.data.ref.parent.child('downvote_count').set(event.data.numChildren());
});

// exports.calculatePopularityOnUpvote = functions.database.ref('/images/{imageID}/upvote_count').onWrite(event => {
// 	const upvote_count = event.data.val();
// 	const imageID = event.params.imageID;
// 	const downvote_count = event.data.adminRef.root.child('images').child(imageID).child('downvote_count').val();
// 	const popularity = upvote_count/downvote_count;
// 	return event.data.ref.parent.child('popularity').set(popularity);
// });

// exports.calculatePopularityOnDownvote = functions.database.ref('/images/{imageID}/downvote_count').onWrite(event => {
// 	const downvote_count = event.data.val();
// 	const imageID = event.params.imageID;
// 	const upvote_count = event.data.adminRef.parent.child('upvote_count').val();
// 	const popularity = upvote_count/downvote_count;
// 	return event.data.ref.parent.child('popularity').set(popularity);
// });